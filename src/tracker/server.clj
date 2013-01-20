(ns tracker.server

  (:use [compojure.core :only (GET PUT POST defroutes)])
  (:use [tracker.views])
  (:require (compojure handler route)
            [ring.util.response :as response]
            [net.cgrand.enlive-html :as h]))

(defroutes app*
  (GET "/" request "Welcome!")
  (GET "/task-input" request (task-input (work-categories)))
  (POST "/task-input" [& params] (handle-task-input params))
  (compojure.route/not-found "This is not what you are looking for."))

(def app (compojure.handler/api app*))

(defn handle-task-input [params]
  (format "Time=(%s), Category=(%s), Tags=(%s)" (:time params) (:work-category params) (:tags params)))

;; ; To run locally:
;; (use '[ring.adapter.jetty :only (run-jetty)])
;; (def server (run-jetty #'app {:port 8080 :join? false}))

;; dummy data for work categories
;; TODO Move to a proper place
(defn work-categories []

  ["Iteration 1", "Uncategorized"]

  )
