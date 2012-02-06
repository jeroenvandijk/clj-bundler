A small demo application that shows how you can use Ruby gems from
Clojure via the JRuby
[RedBridge project](http://kenai.com/projects/jruby/pages/RedBridge). Nearly
all of this code was taken from
[Yoko Harada's blog post](http://yokolet.blogspot.com/2011/09/haml-on-clojure-web-app.html). I've
just namespaced haml and ruby, set the JRuby version to 1.9, and added
some convenience functions in `redbridge-demo.ruby` and
`redbridge-demo.haml`.
