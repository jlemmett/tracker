(ns tracker.input)

(defn read-time
  "Parses input string representing time into a number representing the given time in minutes. Currently only supports strings in the form '#m' and '#' where # is an integer value"
  [input]
  (Integer/valueOf (re-find #"\d+" input)))


(defn add-task-entry [user date task-input tasks]

  (dosync

   (cond

    (not (@tasks user)) (alter tasks assoc user {date [task-input]})

    :else "TBD")

   )

  ;; case: new user

  ;; case: existing user, new date

  ;; case: existing user, existing date, new task


  )
