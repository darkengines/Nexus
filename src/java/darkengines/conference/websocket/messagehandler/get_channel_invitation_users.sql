SELECT u.display_name AS display_name, u.id AS user_id, ci.id AS id FROM channel_invitation AS ci
JOIN `user` AS u ON u.id = ci.user_id
WHERE channel_id = ?;