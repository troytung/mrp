--[2013-08-05T00:00:00.000Z TO 2013-08-05T23:59:59.999Z]

insert into category (name, display_query, query, separated_query) values (
'HTC',
'(htc&(故障|問題))|"htc one"|"htc butterfly"|蝴蝶機|王雪紅|宏達電|htc|"one max"',
'(htc AND ("故障" OR "問題")) OR "htc one" OR "htc butterfly" OR "蝴蝶機" OR "王雪紅" OR "宏達電" OR htc OR "one max"',
'["(","\\"htc\\"","&","(","\\"故障\\"","|","\\"問題\\"",")",")","|","\\"htc one\\"","|","\\"htc butterfly\\"","|","\\"蝴蝶機\\"","|","\\"王雪紅\\"","|","\\"宏達電\\"","|","\\"htc\\"","|","\\"one max\\""]');

insert into category (name, display_query, query, separated_query) values (
'Samsung',
'((samsung&(故障|問題|升級|更新|死機))|"galaxy s3"|"galaxy sIII"|S3|S4|note2|noteII|"note 3"|note3|三星)&!(三星蔥|三星彩|三星鄉)',
'((samsung AND ("故障" OR "問題" OR "升級" OR "更新" OR "死機")) OR "galaxy s3" OR "galaxy sIII" OR "S3" OR "S4" OR "note2" OR "noteII" OR "note 3" OR "note3" OR "三星") AND -("三星蔥" OR "三星彩" OR "三星鄉")',
'["(","(","\\"samsung\\"","&","(","\\"故障\\"","|","\\"問題\\"","|","\\"升級\\"","|","\\"更新\\"","|","\\"死機\\"",")",")","|","\\"galaxy s3\\"","|","\\"galaxy sIII\\"","|","\\"S3\\"","|","\\"S4\\"","|","\\"note2\\"","|","\\"noteII\\"","|","\\"note 3\\"","|","\\"note3\\"","|","\\"三星\\"",")","&","!","(","\\"三星蔥\\"","|","\\"三星彩\\"","|","\\"三星鄉\\"",")"]');

insert into category (name, display_query, query, separated_query) values (
'HTC One',
'(htc&(one|"M7"))|(htc&(new|one))|新一機|"new one"',
'(htc AND (one OR "M7")) OR (htc AND (new OR one)) OR "新一機" OR "new one"',
'["(","\\"htc\\"","&","(","\\"one\\"","|","\\"M7\\"",")",")","|","(","\\"htc\\"","&","(","\\"new\\"","|","\\"one\\"",")",")","|","\\"新一機\\"","|","\\"new one\\""]');

insert into category (name, display_query, query, separated_query) values (
'HTC Butterfly s',
'(htc&(butterfly|蝴蝶))|蝴蝶機|"butterfly s"',
'(htc AND (butterfly OR "蝴蝶")) OR "蝴蝶機" OR "butterfly s"',
'["(","\\"htc\\"","&","(","\\"butterfly\\"","|","\\"蝴蝶\\"",")",")","|","\\"蝴蝶機\\"","|","\\"butterfly s\\""]');

insert into category (name, display_query, query, separated_query) values (
'Apple iPhone',
'iphone|iphone5',
'"iphone" OR "iphone5"',
'["\\"iphone\\"","|","\\"iphone5\\""]');

insert into category (name, display_query, query, separated_query) values (
'Apple iPhone 5s',
'"iphone5s"|"iphone 5s"',
'"iphone5s" OR "iphone 5s"',
'["\\"iphone5s\\"","|","\\"iphone 5s\\""]');

insert into category (name, display_query, query, separated_query) values (
'Apple iPhone 5c',
'iphone5c|"iphone 5c"',
'"iphone5c" OR "iphone 5c"',
'["\\"iphone5c\\"","|","\\"iphone 5c\\""]');

insert into category (name, display_query, query, separated_query) values (
'Sony',
'("sony"&("防水"|"防塵"|"UI"))|("sony"|"xperia")&("z"|"zl"|"z1"|"五吋")',
'(sony AND ("防水" OR "防塵" OR "UI")) OR ("sony" OR "xperia") AND ("z" OR "zl" OR "z1" OR "五吋")',
'["(","\\"sony\\"","&","(","\\"防水\\"","|","\\"防塵\\"","|","\\"UI\\"",")",")","|","(","\\"sony\\"","|","\\"xperia\\"",")","&","(","\\"z\\"","|","\\"zl\\"","|","\\"z1\\"","|","\\"五吋\\"",")"]');


insert into category (name, display_query, query, separated_query) values (
'Sony Xperia Z1',
'"z1"',
'"z1"',
'["\\"z1\\""]');

insert into category (name, display_query, query, separated_query) values (
'HTC One max',
'"one max"',
'"one max"',
'["\\"one max\\""]');

insert into category (name, display_query, query, separated_query) values (
'Samsung Galaxy Note 3',
'"note 3"|"note3"',
'"note 3" OR "note3"',
'["\\"note 3\\"","|","\\"note3\\""]');

insert into category (name, display_query, query, separated_query) values (
'Samsung Galaxy S4',
'"S4"|"galaxy4"',
'"s4" OR "galaxy4"',
'["\\"S4\\"","|","\\"galaxy4\\""]');

--mysql -u root -p  --default-character-set=utf8