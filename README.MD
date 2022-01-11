[![Build Status](https://app.travis-ci.com/ViyChel/job4j_passport.svg?branch=master)](https://app.travis-ci.com/ViyChel/job4j_passport)

# job4j_passport

Demo project about checking expired passports in a microservice system.

The following methods are supported:

- /save, save passport details
- /update?id=*, update passport details
- /delete?id=*, delete passport details
- /find?seria=*, find passports with a given series
- /unavaliabe, find expired passports
  /find-replaceable, find passports that need to be replaced in the next 3 months