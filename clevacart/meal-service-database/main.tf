terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

resource "docker_image" "mysql" {
  name = "mysql:latest"
}

resource "docker_container" "mysql" {
  name  = "MealServiceDatabaseImage"
  image = docker_image.mysql.image_id

  ports {
    internal = 3306
    external = 3307
  }

  env = [
    "MYSQL_ROOT_PASSWORD=top-secret-pw",
    "MYSQL_DATABASE=MealServiceDB",
    "MYSQL_USER=user1",
    "MYSQL_PASSWORD=secretpw"
  ]

  healthcheck {
    test        = ["CMD-SHELL", "mysqladmin ping -h localhost"]
    interval    = "10s"
    timeout     = "20s"
    retries     = 3
    start_period = "30s"
  }
}
