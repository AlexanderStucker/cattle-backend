package com.example;

import com.example.model.Cattle;
import com.example.service.CattleService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/cattle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cattle API", description = "API zum Verwalten von Rindern")
@Transactional
public class CattleResource {

    @Inject
    CattleService cattleService;

    @GET
    @Operation(summary = "Alle Rinder abrufen", description = "Gibt eine Liste aller gespeicherten Rinder zurück")
    public List<Cattle> listAll() {
        return Cattle.listAll();
    }

    @POST
    @Operation(summary = "Neues Rind speichern", description = "Speichert ein neues Rind in der Datenbank")
    public Cattle create(Cattle cattle) {
        cattleService.saveCattleAndSendValidation(cattle);
        return cattle;
    }

    @GET
    @Path("validated")
    @Operation(summary = "Validierte Rinder abrufen", description = "Gibt eine Liste aller validierten Rinder zurück")
    public List<Cattle> listValidated() {
        return Cattle.list("validated", true);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Rind löschen", description = "Löscht ein Rind anhand der ID aus der Datenbank")
    public void delete(@PathParam("id") Long id) {
        cattleService.deleteCattle(id);
    }

}
