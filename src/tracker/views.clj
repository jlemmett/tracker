(ns tracker.views
  (:require [net.cgrand.enlive-html :as h]))


;; The task input view: populates the work categories element with provided category names and shows
;; any existing tasks
(h/deftemplate task-input "resources/task-input.html"
  [categories existing-tasks]
  [:select#work-category :option] (h/clone-for [c categories]
                                               (h/do->
                                                (h/content c)
                                                (h/set-attr :value c)))
  [:div#existing-tasks] (h/content existing-tasks)
)
