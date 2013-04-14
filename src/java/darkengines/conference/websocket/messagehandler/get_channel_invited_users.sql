SELECT ci.id, ci.user_id, ci.channel_id FROM channel_invitation AS ci
WHERE ci.channel_id = ?;