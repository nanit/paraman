# paraman

Paraman is a Clojure library for converting Clojure maps into valid HTTP query
params. It supports arrays and nested objects.

I saw the need for such a library because I found out both clj-http and
http-kit clients do not fully support both arrays, nested objects or arrays
consisting of objects, which some API require (For example, Stripe API).

## Usage

Instead of passing your params map to either clj-http or http-kit :form-params
request option, use the library to convert your params into a valid HTTP query string and
pass it directly to the request :body of either your clj-http or http-kit
request options. 

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
