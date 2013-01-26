(ns tracker.server

  (:use [compojure.core :only (GET PUT POST defroutes)])
  (:use [tracker.views])
  (:use [tracker.input])
  (:require (compojure handler route)
            [ring.util.response :as response]
            [net.cgrand.enlive-html :as h]))


;; in-memory persistence for the tasks
;; structure: {:user1 {:date1 [{task1} {task2} ...], date2 ...}, :user2 ...}
(def ^:private tasks (ref {}))

(defn work-categories []
  ["Iteration 1", "Uncategorized"]
  )



(defn handle-task-input [params]
  (let [user (keyword (:user params))
        date (keyword (:date params))
        time (:time params)
        work-category (keyword (:work-category params))
        tags (:tags params)]

    (add-task-entry user date time work-category tags tasks)
    (print-str @tasks)
    ))


(defroutes app*
  (GET "/" request "Welcome!")
  (GET "/task-input" request (task-input (work-categories)))
  (POST "/task-input" [& params] (handle-task-input params))
  (compojure.route/not-found "This is not what you are looking for."))

(def app (compojure.handler/api app*))

;; ; To run locally:
;; (use '[ring.adapter.jetty :only (run-jetty)])
;; (def server (run-jetty #'app {:port 8080 :join? false})) (def server (run-jetty #'app {:port 8080 :join? false}))
