(ns tracker.views
  (:require [net.cgrand.enlive-html :as h]))

;; TODO: Use this instead of the embedded row in task-input.html
(def task-row (h/html-resource "resources/task-row-snippet.html"))

;; The task input view: populates the work categories element with provided category names and shows
;; any existing tasks
(h/deftemplate task-input "resources/task-input.html"
  [categories existing-tasks]
  [:select#work-category :option] (h/clone-for [c categories]
                                               (h/do->
                                                (h/content c)
                                                (h/set-attr :value c)))

  [:#existing-tasks :table :tr.task-row]
  (h/clone-for [task existing-tasks]
               [:td.time]
               (h/content (:time task))

               [:td.work-category]
               (h/content (str (:work-category task)))

               [:td.tags]
               (h/content (:tags task))

               [:td.description]
               (h/content (:description task))

               )
  )
