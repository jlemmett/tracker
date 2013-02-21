(ns tracker.output)


(defn to-hrs-and-mins [minutes]
  (let [hours (quot minutes 60)
        minutes (rem minutes 60)]

    (cond
     (and (= 0 hours) (= 0 minutes)) ""
     (= 0 hours) (format "%sm" minutes)
     (= 0 minutes) (format "%sh" hours)
     :else (format "%sh %sm" hours minutes))))
