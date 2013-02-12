(ns tracker.server

  (:use [compojure.core :only (GET PUT POST defroutes)])
  (:use [tracker.views])
  (:use [tracker.input])
  (:use [tracker.time])
  (:use [ring.adapter.jetty :only (run-jetty)])
  (:require (compojure handler route)
            [ring.util.response :as response]
            [net.cgrand.enlive-html :as h]))


;; in-memory persistence for the tasks
;; structure: {:user1 {:date1 [{task1} {task2} ...], date2 ...}, :user2 ...}
;(def ^:private tasks (ref {}))

; Some initial data for testing
(def ^:private tasks
  (ref
   {:test-user
    {:2013-02-04 [
                  {:time "45", :work-category :Iteration-1, :tags "jorma tarha", :description "asdf"}
                  {:time "90", :work-category :Uncategorized, :tags "saab pontiac", :description "jobalaba"}
                  {:time "180", :work-category :Uncategorized, :tags "hurlum", :description "sabaton"}
                  {:time "350", :work-category :Iteration-1, :tags "V0001 F0025", :description ""}]}}))

; Some categories for input options
(defn work-categories []
  ["Iteration 1", "Uncategorized"]
  )

(defn handle-task-input [params]
  (let [user (keyword (:user params))
        date (keyword (:date params))
        time (:time params)
        work-category (keyword (:work-category params))
        tags (:tags params)
        description (:description params)]

    (add-task-entry user date
                    {:time time :work-category work-category :tags tags :description description}
                    tasks)


    ))



(defroutes app*
  (GET "/" request (main-template ((main-page-content (current-weeks-date)))))

  ;; currently hard-coded to get tasks of test-user
  (GET "/task-input/:date" [date & params]
       (main-template
        (task-input (work-categories)
                    ((@tasks :test-user) (keyword date))
                    date)))

  (POST "/task-input" [& params]
        (handle-task-input params)
        (response/redirect (format "/task-input/%s" ( :date params))))


  (compojure.route/resources "/" {:root "resources"})
  (compojure.route/not-found "This is not what you are looking for."))

(def app (compojure.handler/api app*))

;; ; To run locally:
;; (use '[ring.adapter.jetty :only (run-jetty)])
;; (def server (run-jetty #'app {:port 8080 :join? false})(def server (run-jetty #'app {:port 8080 :join? false}))


(defn run []
  (def server (run-jetty #'app {:port 8080 :join? false})))
