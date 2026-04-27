CREATE TABLE log(
    idlog SERIAL PRIMARY KEY,
    tableName VARCHAR(50),
    operation VARCHAR(50),
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ancienContenu TEXT,
    nouveauContenu TEXT,
    utilisateur varchar(50)
);

CREATE
OR REPLACE FUNCTION insert_log_function(
    p_tableName VARCHAR,
    p_operation VARCHAR,
    p_ancienContenu TEXT,
    p_nouveauContenu TEXT
) RETURNS void LANGUAGE plpgsql AS $$ 
DECLARE 
	v_idUser INTEGER;
	v_nom_prenom VARCHAR;
BEGIN 
	BEGIN 
		v_idUser := current_setting('app.id_utilisateur', true)::INTEGER;
	EXCEPTION WHEN OTHERS THEN 
		v_idUser := NULL;
	END;
	
	if v_idUser IS NOT NULL THEN 
		SELECT nom || ' ' || prenom
		INTO v_nom_prenom
		from public.utilisateur
		WHERE id_utilisateur = v_idUser;
	END IF;
	

INSERT INTO
    log(
        tableName,
        operation,
        ancienContenu,
        nouveauContenu,
		utilisateur
    )
VALUES
    (
        p_tableName,
        p_operation,
        p_ancienContenu,
        p_nouveauContenu,
		v_nom_prenom
    );

END;
$$;

-- FRANCHISE -- 
select * from franchise f;
-- INSERT -- 
CREATE
OR REPLACE FUNCTION trigger_franchise_insert() RETURNS TRIGGER AS $$ BEGIN PERFORM insert_log_function(
    'franchise',
    'INSERT',
    '',
    'ID: ' || NEW.id_franchise || ', Nom Franchise: ' || NEW.nom_franchise || ', Siege Social: ' || new.siege_social || ', ID Gerant: ' || new.id_gerant
);

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_insert
AFTER
INSERT
    ON franchise FOR EACH ROW EXECUTE FUNCTION trigger_franchise_insert();

-- UPDATE -- 
CREATE
OR REPLACE FUNCTION trigger_franchise_update() RETURNS TRIGGER AS $$ BEGIN PERFORM insert_log_function(
    'franchise',
    'UPDATE',
    'ID: ' || OLD.id_franchise || ', Nom Franchise: ' || OLD.nom_franchise  || ', Siege Social: ' || OLD.siege_social || ', ID Gerant: ' || OLD.id_gerant,
    'ID: ' || NEW.id_franchise || ', Nom Franchise: ' || NEW.nom_franchise  || ', Siege Social: ' || new.siege_social || ', ID Gerant: ' || new.id_gerant
);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_update
AFTER
UPDATE
    ON franchise FOR EACH ROW EXECUTE FUNCTION trigger_franchise_update();

-- DELETE --
CREATE
OR REPLACE FUNCTION trigger_franchise_delete() RETURNS TRIGGER AS $$ BEGIN PERFORM insert_log_function(
    'franchise',
    'DELETE',
    'ID: ' || OLD.id_franchise || ', Nom Franchise: ' || OLD.nom_franchise  || ', Siege Social: ' || OLD.siege_social || ', ID Gerant: ' || OLD.id_gerant,
    ''
);
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_delete
AFTER
    DELETE ON franchise FOR EACH ROW EXECUTE FUNCTION trigger_franchise_delete();


-- CINEMA --
select * from cinema c;
-- INSERT --
CREATE
OR REPLACE FUNCTION trigger_cinema_insert() RETURNS TRIGGER AS $$ BEGIN PERFORM insert_log_function(
    'cinema',
    'INSERT',
    '',
    'ID: ' || NEW.id_cinema || ', Denomination: ' || NEW.denomination || ', Adresse: ' || new.adresse || ', Ville: ' || new.ville || ', ID Franchise' || new.id_franchise
);

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_insert
AFTER
INSERT
    ON cinema FOR EACH ROW EXECUTE FUNCTION trigger_cinema_insert();

-- UPDATE -- 
CREATE
OR REPLACE FUNCTION trigger_cinema_update() RETURNS TRIGGER AS $$ BEGIN PERFORM insert_log_function(
    'cinema',
    'UPDATE',
    'ID: ' || OLD.id_cinema || ', Denomination: ' || OLD.denomination || ', Adresse: ' || OLD.adresse || ', Ville: ' || OLD.ville || ', ID Franchise' || OLD.id_franchise,
    'ID: ' || NEW.id_cinema || ', Denomination: ' || NEW.denomination || ', Adresse: ' || new.adresse || ', Ville: ' || new.ville || ', ID Franchise' || new.id_franchise
);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_update
AFTER
UPDATE
    ON cinema FOR EACH ROW EXECUTE FUNCTION trigger_cinema_update();

-- DELETE --
CREATE
OR REPLACE FUNCTION trigger_cinema_delete() RETURNS TRIGGER AS $$ BEGIN PERFORM insert_log_function(
    'cinema',
    'DELETE',
    'ID: ' || OLD.id_cinema || ', Denomination: ' || OLD.denomination || ', Adresse: ' || OLD.adresse || ', Ville: ' || OLD.ville || ', ID Franchise' || OLD.id_franchise,
    ''
);
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_delete
AFTER
    DELETE ON cinema FOR EACH ROW EXECUTE FUNCTION trigger_cinema_delete();

select * from log l ;







