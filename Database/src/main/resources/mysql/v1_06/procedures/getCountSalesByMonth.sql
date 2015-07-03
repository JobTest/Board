DROP PROCEDURE IF EXISTS `getCountSalesByMonth`;
#
CREATE PROCEDURE `getCountSalesByMonth`
  (`year`      INT,
   `bank_attr` VARCHAR(15),
   `ticket_id` INT)
  BEGIN
    SELECT
      t.month,
      SUM(t.value) AS `value`
    FROM ticket_items t
      LEFT JOIN bank b ON b.id = t.bank_id
    WHERE t.year = year
          AND t.financial_type = 1
          AND b.attr_id = bank_attr COLLATE utf8_unicode_ci
          AND (t.ticket_type = ticket_id OR ticket_id IS NULL)
    GROUP BY t.month;
  END;
#