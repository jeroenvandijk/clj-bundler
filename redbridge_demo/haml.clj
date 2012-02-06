(ns redbridge-demo.haml
  (:require [redbridge-demo.ruby :as rb]))

(defonce haml-engine
  (do (rb/require-gems "rubygems" "haml-3.1.3/gem/haml")
      (rb/execute "Haml::Engine")))

(defn haml
  "Takes HAML as a string and returns a HAML::Engine RubyObject.

   Usage: (haml->html \"%html\") ;=> #<RubyObject #<Haml::Engine:0x33802282>>"
  [haml-str]
  (rb/call-method haml-engine "new" haml-str))

(defn render
  "Takes a HAML::Engine RubyObject and returns the resulting HTML output.

   Usage: (render (haml->html \"%html\") ;=> \"<html></html>\n\""
  [haml-obj]
  (rb/call-method haml-obj "render"))