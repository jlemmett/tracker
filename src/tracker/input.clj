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

  (let [dates-with-zero-times (apply merge (map (fn [date] (assoc {} date 0)) dates))
        dates-with-existing-times
        (apply merge
               (map (fn [x]
                      (assoc {}
                        (nth x 0)
                        (reduce + (map :time (flatten (nth x 1))))))

                    (select-keys dates-and-tasks dates)))
        ]




    (merge dates-with-zero-times dates-with-existing-times)))
