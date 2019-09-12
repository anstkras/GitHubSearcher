# GitHubSearcher


GitHubSearcher takes a code fragment and performs search for this fragment using GitHub API.
Since the API query is not complicated, only `java.net` library is used for this. 
There is one problem with using GitHub API for code search - authorization is required in order 
to search without restriction by user, organization, or repository. So that is the reason why the application 
requires username and password. For prossesing json response `minimal-json` is used because
the prossesing is quite simple so no heavyweight library is needed.

## Build
`./gradlew build`

## Run
`java -jar build/libs/GitHubSearcher.jar`
