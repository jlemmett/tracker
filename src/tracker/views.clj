(ns tracker.views
  (:require [net.cgrand.enlive-html :as h]))


;; Templates

(h/deftemplate task-input "resources/task-input.html"
  [categories]
  [:select#work-category :option] (h/clone-for [c categories]
                                               (h/do->
                                                (h/content c)
                                                (h/set-attr :value c))))
