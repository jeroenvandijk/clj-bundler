(ns jruby-gem.core
  (:import [org.jruby CompatVersion]
           [org.jruby.embed ScriptingContainer LocalContextScope]))
; 
(defn set-container-version
 "Sets the version of a container to RUBY1_9"
 [container]
 (. container setCompatVersion (. CompatVersion RUBY1_9)))

(defonce container
 (let [container (ScriptingContainer. LocalContextScope/SINGLETHREAD)]
   (do (set-container-version container)
       container)))

(defn execute
  "Takes a string of Ruby code and runs it.

  Usage: (execute \"'hello'.reverse\") ;=> \"olleh\""
  [rb-string]
  (. container runScriptlet rb-string))
  
(defn require-gem
  "Requires a Ruby gem.

   Usage: (require-gem \"rubygems\") ;=> true"
  [gem version]
  (execute (str "
    $:.unshift File.join(File.dirname(__FILE__), 'lib/jruby/1.9/gems/" gem "-" version "/lib')
    require '" gem "'
  ")))

(defn require-gems
  "Requires multiple Ruby gems.

   Usage: (require-gems \"rubygems\" \"haml-3.1.3/gem/haml\") ;=> true"
  [& gems]
  (doseq [gem gems]
    (apply require-gem gem)))

(defn call-method
  "Calls a method on an object."
  ([obj method]
     (. container callMethod obj method Object))
  ([obj method arg]
     (. container callMethod obj method arg Object)))

; ------- HAML ---------
(defonce haml-engine
  (do (require-gems ["haml" "3.1.4"])
    (execute "Haml::Engine")))

(defn haml
  "Takes HAML as a string and returns a HAML::Engine RubyObject.

  Usage: (haml->html \"%html\") ;=> #<RubyObject #<Haml::Engine:0x33802282>>"
  [haml-str]
  (call-method haml-engine "new" haml-str))

(defn render
 "Takes a HAML::Engine RubyObject and returns the resulting HTML output.

  Usage: (render (haml->html \"%html\") ;=> \"<html></html>\n\""
  [haml-obj]
  (call-method haml-obj "render"))
  
(def home-page (haml (slurp "index.haml")))

(println (call-method home-page "render"))

; ------- SASS ---------
(defonce sass-engine
  (do (require-gems ["sass" "3.1.14"])
    (execute "Sass::Engine")))

(defn sass
  "Takes HAML as a string and returns a HAML::Engine RubyObject.

  Usage: (haml->html \"%html\") ;=> #<RubyObject #<Haml::Engine:0x33802282>>"
  [sass-str]
  (call-method sass-engine "new" sass-str))

(defn render-sass
 "Takes a HAML::Engine RubyObject and returns the resulting HTML output.

  Usage: (render (haml->html \"%html\") ;=> \"<html></html>\n\""
  [haml-obj]
  (call-method haml-obj "render"))
  
(def hello-sass (sass (slurp "hello.sass")))

(println (call-method hello-sass "render"))



(println "done")
(System/exit 0) 
