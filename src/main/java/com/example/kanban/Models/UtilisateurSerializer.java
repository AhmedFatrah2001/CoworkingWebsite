package com.example.kanban.serializers;

import com.example.kanban.Models.Utilisateur;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UtilisateurSerializer extends StdSerializer<Utilisateur> {

    public UtilisateurSerializer() {
        super(Utilisateur.class);
    }

    @Override
    public void serialize(Utilisateur value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("username", value.getUsername());
        gen.writeStringField("email", value.getEmail());
        gen.writeEndObject();
    }
}
