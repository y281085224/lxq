class a():
    def __init__(self):
        self.m = "hello"

    def test_a(self):
        print(111111111)

    def test_b(self):
        self.test_a()
        n = self.m + "world"
        print(n)


if __name__ == '__main__':
    a().test_b()