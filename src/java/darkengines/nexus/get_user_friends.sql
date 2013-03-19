SELECT u.`id`, u.`email`, u.`display_name`, fb.id is not null as reverse_friendship FROM `friendship` AS f 
LEFT JOIN `user` AS u ON f.target = u.id
LEFT OUTER JOIN friendship AS fb ON fb.owner = u.id
WHERE f.owner = ?;