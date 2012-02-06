(ns redbridge-demo.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.string :as s :only [replace]]
            [redbridge-demo.haml :as haml]
            [redbridge-demo.ruby :as rb]))

(def home-page (haml/haml (slurp "index.haml")))

(defn app [req]
  {:status 200
   :headers {"ContentType" "text/html"}
   :body (rb/call-method home-page "render")})

(defn -main []
  (jetty/run-jetty app {:port 8080}))