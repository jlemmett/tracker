(ns tracker.views
  (:require [net.cgrand.enlive-html :as h]))


(h/defsnippet main-page-content "resources/snippets.html" [:#main-page-content] [])

(h/defsnippet header "resources/snippets.html" [:#header] [])

(h/defsnippet task-rows "resources/task-row-snippet.html" [:tr.task-row]
             [tasks]

             [:tr.task-row]
             (h/clone-for [task tasks]
                          [:td.time]
                          (h/content (:time task))

                          [:td.work-category]
                          (h/content (str (:work-category task)))

                          [:td.tags]
                          (h/content (:tags task))

                          [:td.description]
                          (h/content (:description task))))

;; The task input view: populates the work categories element with provided category names and shows
;; any existing tasks
(h/defsnippet task-input "resources/task-input.html" [:#task-input]
  [categories existing-tasks]
  [:select#work-category :option] (h/clone-for [c categories]
                                               (h/do->
                                                (h/content c)
                                                (h/set-attr :value c)))

  [:#existing-tasks :table] (h/content (task-rows existing-tasks)))


(h/deftemplate main-template "resources/base-template.html" [content]
  [:#header] (h/content  (header))
  [:#main] (h/content content))
