DROP PROCEDURE IF EXISTS `getCountSalesByTicketType`;
#
CREATE PROCEDURE `getCountSalesByTicketType`
  (`year`      INT,
   `month`     INT,
   `bank_attr` VARCHAR(15))
  BEGIN
    SELECT
      ticket_type,
      SUM(CASE WHEN t.financial_type = 1 THEN value
          ELSE 0 END) AS sales_cnt,
      SUM(CASE WHEN t.financial_type = 3 THEN value
          ELSE 0 END) AS revenue,
      SUM(CASE WHEN t.financial_type = 2 THEN value
          ELSE 0 END) AS turnover
    FROM ticket_items t
    WHERE t.year = year AND (t.month = month OR month IS NULL) AND t.bank_id = (SELECT id
                                                                                FROM bank
                                                                                WHERE attr_id = bank_attr COLLATE utf8_unicode_ci)
    GROUP BY ticket_type
    ORDER BY sales_cnt DESC;

  END;
#