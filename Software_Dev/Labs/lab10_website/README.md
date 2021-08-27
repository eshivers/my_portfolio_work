
---

<div align="center">

# Heroku Node+PostgreSQL Docker Example

</div>


## Description
Heroku Node+Postgres with Docker Example for CU Boulder CSCI 3308 Spring 2021 Lab 10


## How to run

### Setup
Build the docker images
```bash
docker-compose build
```


Once you setup the [heroku-cli](https://devcenter.heroku.com/articles/heroku-cli) locally, get your API token with
```bash
heroku auth:token
```

Now copy the token into [`/heroku/.env`](/heroku/.env) as an environment variable `HEROKU_API_KEY`. This allows us to run the heroku-cli in a docker container. Most heroku commands can be ran locally, but we'll use this container for setting up [Heroku Postgres](https://devcenter.heroku.com/categories/heroku-postgres).


### Heroku Postgres
We can access the heroku-cli container:
```bash
docker-compose run heroku-cli
```
Here postgres is installed, all we need to do is run the following.

```bash
heroku pg:psql --app <your-app-name>
```

### Local Development

We use docker-compose for local development. This uses a specific target stage (`devapp`) of our Dockerfile to run nodemon. We also have a local database container `db`.

 We can run the development environment with:
```bash
docker-compose up
```

### Deploying to Heroku
The final stage of our Dockerfile is `prodapp`, which contains no development dependencies (e.g. nodemon).

To deploy it to Heroku, run the following:
```bash
# login to Registry
heroku container:login
# build and push to registry
heroku container:push web -a <app_name>
# release
heroku container:release web -a <app_name>
```

To check everything worked, open you're browser.
```bash
heroku open -a <app_name>
```

For more details, see the [Heroku Container Registry & Runtime Documentation](https://devcenter.heroku.com/articles/container-registry-and-runtime)
