import json

check_actions = {}

unparsed_data = open('config.json')
data = json.load(unparsed_data)

hostname = data['hostname']
port = int(data['port'])
threads = int(data['threads'])

for check in data['checks']:
    check_actions[check['check_type'] + check['check_name']] = {
        'action': check['action'],
        'max_violations': check['max_violations']
    }

unparsed_data.close()


def get_check_data(check_name, check_type):
    return check_actions[check_name + check_type]
