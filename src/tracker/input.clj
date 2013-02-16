(ns tracker.input)

(defn read-time
  "Parses input string representing time into a number representing the given time in minutes. Currently only supports strings in the form '#m' and '#' where # is an integer value"
  [input]
  (Integer/valueOf (re-find #"\d+" input)))


(defn add-task-entry [user date task-input tasks]

  ;; input validation?

  (dosync

   (cond
    ;; new user
    (not (@tasks user)) (alter tasks assoc user {date [task-input]})

    ;; existing user, new date
    (not ((@tasks user) date)) (alter tasks assoc-in [user date]  [task-input])

    ;; case: existing user, existing date, new task
    ((@tasks user) date) (alter tasks assoc-in [user date] (conj ((@tasks user) date) task-input)))))

(defn dates-and-worked-hours [dates dates-and-tasks]
  ; work in progress
  (select-keys dates-and-tasks dates)
;  (for [[k v] {1 2 3 4}] (+ k v)) => (3 7)


  )
