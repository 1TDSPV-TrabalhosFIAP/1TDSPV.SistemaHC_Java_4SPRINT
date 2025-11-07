package br.com.fiap.sistemahc.resource;

import br.com.fiap.sistemahc.model.Usuario;
import br.com.fiap.sistemahc.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @Inject
    UsuarioService service;

    @GET
    public List<Usuario> listar() { return service.listar(); }

    @GET
    @Path("/{id}")
    public Usuario buscar(@PathParam("id") String id) { return service.buscar(id); }

    @POST
    public Response criar(@Valid Usuario u) {
        Usuario criado = service.criar(u);
        return Response.status(Response.Status.CREATED).entity(criado).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") String id, @Valid Usuario u) {
        Usuario atualizado = service.atualizar(id, u);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") String id) {
        service.deletar(id);
        return Response.noContent().build();
    }
}

