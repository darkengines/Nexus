SELECT COUNT(id) > 1 AS are_friends FROM friendship where (target = ? AND owner = ? ) OR (target=? AND owner=?);