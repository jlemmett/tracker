(ns tracker.server

  (:use [compojure.core :only (GET PUT POST defroutes)])
  (:require (compojure handler route)
            [ring.util.response :as response]
            [net.cgrand.enlive-html :as h]))



(defroutes app*
  (GET "/" request "Welcome!")
  (GET "/input-proto" request (task-input (work-categories)))
  (POST "/task-input" request "Saving task input TBD")
  (compojure.route/not-found "This is not what you are looking for."))

(def app (compojure.handler/api app*))

;; ; To run locally:
;; (use '[ring.adapter.jetty :only (run-jetty)])
;; (def server (run-jetty #'app {:port 8080 :join? false}))


;; Templates
;; TODO: Move to e.g. views.clj
(h/deftemplate task-input "resources/task-input.html"
  [categories]
  [:select#work-category :option] (h/clone-for [c categories]
                                               (h/do->
                                                (h/content c)
                                                (h/set-attr :value c))
                                               ))







;; dummy data for work categories
;; TODO Move to a proper place
(defn work-categories []

  ["Iteration 1", "Uncategorized"]

  )
