from flask import Flask
from flask import request
from flask import Response

from concurrent.futures import ThreadPoolExecutor

import config
import json

app = Flask(__name__)
executor = ThreadPoolExecutor(config.threads)

@app.route('/players/<id>/', methods=['POST'])
def handle_players_route(id):
    data = request.get_json()
    print(id)
    print(data)

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
