import json

unparsed_data = open('config.json')
data = json.load(unparsed_data)

hostname = data['hostname']
port = int(data['port'])
threads = int(data['threads'])

unparsed_data.close()
