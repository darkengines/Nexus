SELECT ci.id, ci.channel_id, ci.user_id FROM channel_invitation AS ci
WHERE user_id = ?;