from flask import Flask
from flask import request
from flask import Response

from check import check_manager
from concurrent.futures import ThreadPoolExecutor
from data.player_data import PlayerData

import config
import json

app = Flask(__name__)
executor = ThreadPoolExecutor(config.threads)
player_data_manager = {}


def handle_checks(data, event):
    for check in data.checks:
        check.handle(event)


@app.route('/players/<id>', methods=['POST'])
def handle_players_route(id):
    player_data = None
    data = request.json()

    if id not in player_data_manager:
        player_data = PlayerData(id)
        check_manager.load_checks(player_data)
        
        player_data_manager[id] = player_data
    else:
        player_data = player_data_manager[id]

    executor.submit(handle_checks, player_data, data)

    return Response(json.dumps({
        'violations': {
            'a': {
                'checkName': 'Test',
                'checkType': 'A',
                'violations': 1,
                'maxViolations': 2
            }
        }
    }), status=200)


if __name__ == "__main__":
    app.run(config.hostname, config.port)
