(ns tracker.views
  (:require [net.cgrand.enlive-html :as h])
  (:use [tracker.time])
  (:use [tracker.output]))

(h/defsnippet date-links "resources/snippets.html" [:div.date-container] [date-data]
  [:div.date-container]
  (h/clone-for [data-for-date date-data]

               [:a.date]
               (h/do->
                (h/content (str (:display-value  data-for-date)))
                (h/set-attr :href (format "task-input/%s" (str (:date-value data-for-date)))))

                [:div.date-content]
                (h/do->
                 (h/content (to-hrs-and-mins (:time data-for-date))))))


(defn disp-val-and-date-val [date]
  {:display-value (format-date-with display-formatter (first date)) :date-value (format-date (first date)) :time (last date)})

(defn display-val-date-val-pairify [dates]
  (map disp-val-and-date-val dates))


(h/defsnippet main-page-content "resources/snippets.html" [:div#main-page-content] [dates]
  [:div#dates]
  (h/content  (date-links (display-val-date-val-pairify dates))))

(h/defsnippet header "resources/snippets.html" [:#header] [])

(h/defsnippet task-rows "resources/task-row-snippet.html" [:tr.task-row]
             [tasks]

             [:tr.task-row]
             (h/clone-for [task tasks]
                          [:td.time]
                          (h/content (to-hrs-and-mins (:time task)))

                          [:td.work-category]
                          (h/content (str (:work-category task)))

                          [:td.tags]
                          (h/content (:tags task))

                          [:td.description]
                          (h/content (:description task))))

;; The task input view: populates the work categories element with provided category names and shows
;; any existing tasks
(h/defsnippet task-input "resources/task-input.html" [:#task-input]
  [categories existing-tasks date]

  [:#visible-date] (h/content (format-date-with basic-formatter date))

  [:input#date] (h/set-attr :value (format-date-with basic-formatter date))

  [:select#work-category :option] (h/clone-for [c categories]
                                               (h/do->
                                                (h/content c)
                                                (h/set-attr :value c)))

  [:#existing-tasks :table :tbody] (h/content (task-rows existing-tasks)))


(h/deftemplate main-template "resources/base-template.html" [content]
  [:#header] (h/content  (header))
  [:#main] (h/content content))
