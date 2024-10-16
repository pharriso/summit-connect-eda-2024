from locust import HttpUser, task

class restHeroesTest(HttpUser):
    @task(3)
    def heroes_getall_heroes(self):
        self.client.get("/api/heroes")
    @task
    def heroes_getrandon_heroes(self):
        self.client.get("/api/heroes/random")