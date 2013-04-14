SELECT u.id, u.display_name FROM channel_participant AS cp
JOIN `user` AS u ON u.id = cp.user_id
WHERE cp.channel_id = ?;