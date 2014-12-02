(ns example.core
  (:use compojure.core)
  (:require
    [ring.adapter.jetty :as jetty]
    [clostache.parser :as clostache]))

;rendering
(defn read-template [template-name]
  (slupr (clojure.java.io/resource
          (str "templates/" template-name ".mustache"))))

(defn render-template [template-file params]
  (clostache/render (read-template template-file) params))

; View
(defn index []
  (render-template "index" {:greeting "Bonjour"}))

; Routing

(defroutes main-routes
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "404 Not Found"))

; Server
(defn -main []
  (let [port (Integer/partInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty main-routes {:port port})))


