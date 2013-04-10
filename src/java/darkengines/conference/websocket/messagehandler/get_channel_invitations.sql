SELECT ci.id, ci.channel_id, c.name AS channel_name FROM channel_invitation AS ci
JOIN channel AS c ON c.id = ci.channel_id
WHERE user_id = ?;