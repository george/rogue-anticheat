import json

unparsed_data = open('config.json')
data = json.load(unparsed_data)

hostname = data['hostname']
port = int(data['port'])

unparsed_data.close()
