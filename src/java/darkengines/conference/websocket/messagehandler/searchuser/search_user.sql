SELECT u.id, u.email, u.display_name, f.id IS NOT NULL AS is_friend FROM `user` AS u
LEFT OUTER JOIN friendship AS f ON f.owner = ? AND f.target = u.id
WHERE u.email RLIKE ?;