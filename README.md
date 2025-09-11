# crud-endpoint-auto-tests

Code written for a job interview in September 2025

Backend CRUD endpoint - tests to be thorough.

Written in:

- Java
- Serenity
- Cucumber BDD
- REST Assured

## How to run

1/ Set up environment variables for:
`API_USER` and `API_PASS`

2/ Run the tests with
`maven clean verify`

3/ Tests are stored in the report folder with a timestamp
configured in the `serenity.conf` file under `serenity.outputDirectory`

4/ After a couple runs you can aggregate the results with:
`mvn serenity:aggregate -Dserenity.outputDirectory=reports/serenity`
where `reports/serenity` is my currently configured output folder.