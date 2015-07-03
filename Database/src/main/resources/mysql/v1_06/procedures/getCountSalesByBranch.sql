DROP PROCEDURE IF EXISTS getCountSalesByBranch;
#
CREATE PROCEDURE `getCountSalesByBranch`
  (`year`       INT,
   `month`      INT,
   `bank_attr`  VARCHAR(15),
   `ticket_id`  INT,
   `channel_id` INT)
  BEGIN
    SELECT
      branch,
      SUM(t.value) AS value
    FROM ticket_items t
      LEFT JOIN bank b ON b.id = t.bank_id
      LEFT JOIN channel c ON c.id = t.channel_id
      LEFT JOIN channel_output ou ON ou.id = c.fkey_channel_output
    WHERE t.year = year
          AND (t.month = month OR month IS NULL)
          AND (t.ticket_type = ticket_id OR ticket_id IS NULL)
          AND b.attr_id = bank_attr COLLATE utf8_unicode_ci
          AND t.financial_type = 4
          AND (ou.id = channel_id OR channel_id IS NULL)
    GROUP BY branch
    ORDER BY value DESC;
  END;
#