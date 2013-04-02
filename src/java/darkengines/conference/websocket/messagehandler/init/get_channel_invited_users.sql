SELECT u.id FROM channel_invitation AS ci
JOIN `user` AS u ON u.id = ci.user_id
WHERE channel_id = ?