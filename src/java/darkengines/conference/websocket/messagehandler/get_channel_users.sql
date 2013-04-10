SELECT u.id, u.display_name FROM channel_participant AS cp
INNER JOIN `user` AS u ON u.id = cp.user_id
WHERE cp.channel_id = ?;