from check.checks.auto_clicker_a import AutoClickerA


def load_checks(data):
    data.checks = {
        AutoClickerA(data)
    }
