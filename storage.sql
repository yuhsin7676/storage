--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6
-- Dumped by pg_dump version 13.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: buy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.buy (
    id integer NOT NULL,
    storage_id integer NOT NULL,
    items text NOT NULL
);


ALTER TABLE public.buy OWNER TO postgres;

--
-- Name: buy_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.buy_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.buy_id_seq OWNER TO postgres;

--
-- Name: buy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.buy_id_seq OWNED BY public.buy.id;


--
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    id integer NOT NULL,
    price_buy integer,
    price_sale integer,
    name character varying
);


ALTER TABLE public.item OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.item_id_seq OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.item_id_seq OWNED BY public.item.id;


--
-- Name: item_in_storage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_in_storage (
    id integer NOT NULL,
    item_id integer NOT NULL,
    storage_id integer NOT NULL,
    number integer NOT NULL
);


ALTER TABLE public.item_in_storage OWNER TO postgres;

--
-- Name: iten_in_storage_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.iten_in_storage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.iten_in_storage_id_seq OWNER TO postgres;

--
-- Name: iten_in_storage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.iten_in_storage_id_seq OWNED BY public.item_in_storage.id;


--
-- Name: move; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.move (
    id integer NOT NULL,
    storage_from integer NOT NULL,
    storage_to integer NOT NULL,
    items text NOT NULL
);


ALTER TABLE public.move OWNER TO postgres;

--
-- Name: move_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.move_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.move_id_seq OWNER TO postgres;

--
-- Name: move_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.move_id_seq OWNED BY public.move.id;


--
-- Name: sale; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sale (
    id integer NOT NULL,
    storage_id integer NOT NULL,
    items text NOT NULL
);


ALTER TABLE public.sale OWNER TO postgres;

--
-- Name: sale_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sale_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sale_id_seq OWNER TO postgres;

--
-- Name: sale_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sale_id_seq OWNED BY public.sale.id;


--
-- Name: storage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storage (
    id integer NOT NULL,
    name character varying(128)
);


ALTER TABLE public.storage OWNER TO postgres;

--
-- Name: storage_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.storage_id_seq OWNER TO postgres;

--
-- Name: storage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storage_id_seq OWNED BY public.storage.id;


--
-- Name: buy id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy ALTER COLUMN id SET DEFAULT nextval('public.buy_id_seq'::regclass);


--
-- Name: item id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item ALTER COLUMN id SET DEFAULT nextval('public.item_id_seq'::regclass);


--
-- Name: item_in_storage id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_in_storage ALTER COLUMN id SET DEFAULT nextval('public.iten_in_storage_id_seq'::regclass);


--
-- Name: move id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.move ALTER COLUMN id SET DEFAULT nextval('public.move_id_seq'::regclass);


--
-- Name: sale id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale ALTER COLUMN id SET DEFAULT nextval('public.sale_id_seq'::regclass);


--
-- Name: storage id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage ALTER COLUMN id SET DEFAULT nextval('public.storage_id_seq'::regclass);


--
-- Data for Name: buy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.buy (id, storage_id, items) FROM stdin;
348	713	[{id: -1, number: 3, price: 150 }]
364	753	[{id: -1, number: 3, price: 150 }]
304	597	[{id: -1, number: 3, price: 150 }]
305	598	[{id: -1, number: 3, price: 150 }]
306	599	[{id: -1, number: 3, price: 150 }]
307	600	[{id: -1, number: 3, price: 150 }]
308	601	[{id: -1, number: 3, price: 150 }]
310	603	[{id: -1, number: 3, price: 150 }]
314	607	[{id: -1, number: 3, price: 150 }]
316	625	[{id: -1, number: 3, price: 150 }]
332	674	[{id: -1, number: 3, price: 150 }]
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.item (id, price_buy, price_sale, name) FROM stdin;
196	140	160	New item
198	140	160	New item
201	140	160	New item
212	140	160	New item
223	140	160	New item
\.


--
-- Data for Name: item_in_storage; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.item_in_storage (id, item_id, storage_id, number) FROM stdin;
482	-1	597	3
484	-1	599	3
486	-1	601	3
499	-1	626	3
504	-1	632	3
\.


--
-- Data for Name: move; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.move (id, storage_from, storage_to, items) FROM stdin;
68	628	629	[{id: -1, number: 3}]
73	669	670	[{id: -1, number: 3}]
78	708	709	[{id: -1, number: 3}]
83	748	749	[{id: -1, number: 3}]
\.


--
-- Data for Name: sale; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sale (id, storage_id, items) FROM stdin;
94	633	[{id: -1, number: 3}]
95	634	[{id: -1, number: 3, price: 160}]
100	664	[{id: -1, number: 3, price: 160}]
105	714	[{id: -1, number: 3, price: 160}]
110	754	[{id: -1, number: 3, price: 160}]
\.


--
-- Data for Name: storage; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.storage (id, name) FROM stdin;
626	\N
627	\N
632	\N
633	\N
595	New storage
596	New storage
597	\N
599	\N
601	\N
608	New storage
609	New storage
611	New storage
666	New storage
615	New storage
757	New storage
618	New storage
621	New storage
675	\N
676	New storage
717	New storage
\.


--
-- Name: buy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.buy_id_seq', 364, true);


--
-- Name: item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_id_seq', 225, true);


--
-- Name: iten_in_storage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.iten_in_storage_id_seq', 604, true);


--
-- Name: move_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.move_id_seq', 84, true);


--
-- Name: sale_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sale_id_seq', 111, true);


--
-- Name: storage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storage_id_seq', 759, true);


--
-- Name: buy buy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy
    ADD CONSTRAINT buy_pkey PRIMARY KEY (id);


--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: move move_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.move
    ADD CONSTRAINT move_pkey PRIMARY KEY (id);


--
-- Name: sale sale_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale
    ADD CONSTRAINT sale_pkey PRIMARY KEY (id);


--
-- Name: storage storage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage
    ADD CONSTRAINT storage_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

