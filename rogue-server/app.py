from flask import Flask, jsonify
from flask import request
from flask import Response

from check import check_manager
from concurrent.futures import ThreadPoolExecutor
from data.player_data import PlayerData

import config
import json
import logging

log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)

app = Flask(__name__)
executor = ThreadPoolExecutor(config.threads)
player_data_manager = {}


def handle_checks(data, packet):
    data.handle_packet(packet)


@app.route('/players/<id>', methods=['POST'])
def handle_players_route(id):
    player_data = None
    data = jsonify(request.form.to_dict()).get_json()

    for key in data:
        data = json.loads(key)
        break

    if 'action' in data:
        player_data_manager.pop(id)
        return

    if id not in player_data_manager:
        player_data = PlayerData(id)
        check_manager.load_checks(player_data)

        player_data_manager[id] = player_data
    else:
        player_data = player_data_manager[id]

    executor.submit(handle_checks, player_data, data)

    violations = []
    if player_data.has_violations():
        violations = player_data.get_and_pop_violations()
    return Response(json.dumps({
        'violations': violations
    }), status=200)


if __name__ == "__main__":
    app.run(config.hostname, config.port, debug=False)
