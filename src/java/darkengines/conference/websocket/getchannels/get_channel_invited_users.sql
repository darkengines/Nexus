SELECT u.id, u.email, u.display_name FROM channel_invitation AS ci
INNER JOIN `user` AS u ON u.id = ci.user_id
WHERE ci.channel_id = ?;