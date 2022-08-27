from check.checks.autoclicker.auto_clicker_a import AutoClickerA
from check.checks.speed.speed_a import SpeedA


def load_checks(data):
    data.checks = {
        AutoClickerA(data),

        SpeedA(data)
    }
