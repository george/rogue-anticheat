import math
import statistics


def hypot(a, b):
    return math.sqrt(
        math.pow(abs(a.x - b.x), 2) +
        math.pow(abs(a.z - b.z), 2)
    )


def get_stdev(numbers):
    return statistics.stdev(numbers)
