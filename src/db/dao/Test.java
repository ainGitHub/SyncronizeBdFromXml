package db.dao;

public class Test {
/*

    @Override
    public void update(PessoaDTO pessoaDTO) throws PersistenceExceptions {

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "UPDATE tb_pessoa SET"
                    + " nome = ?, "
                    + " cpf = ?,"
                    + " endereco = ?,"
                    + " sexo = ?,"
                    + " dt_nasc = ? WHERE id_pessoa = ?";

			*//*No PreparedStatement n?o se usa ; nofonal de cada query, porque d? erro...*//*

            PreparedStatement statement = connection.prepareStatement(sql); // Ctr + 1 (atalho)
            statement.setString(1, pessoaDTO.getNome());
            statement.setLong(2, pessoaDTO.getCpf());
            statement.setString(3, pessoaDTO.getEndereco());
            statement.setString(4, String.valueOf(pessoaDTO.getSexo()));
            statement.setDate(5, new Date(pessoaDTO.getDtNasc().getTime()));
            statement.setInt(6, pessoaDTO.getIdPessoa());
            statement.execute();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceExceptions(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer idPessoa) throws PersistenceExceptions {

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "DELETE FROM tb_pessoa WHERE id_pessoa=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPessoa);
            statement.execute();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceExceptions(e.getMessage(), e);
        }

    }


    public void deleteAll() throws PersistenceExceptions {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "DELETE FROM tb_pessoa";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceExceptions(e.getMessage(), e);
        }


    }

    @Override
    public List<PessoaDTO> listarTodos() throws PersistenceExceptions {

        List<PessoaDTO> listaPessoas = new ArrayList<PessoaDTO>();


        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT * FROM tb_pessoa";
            //Prepare to get information in SQL db.
            PreparedStatement statement = connection.prepareStatement(sql);

            // Save the result of select..
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PessoaDTO pessoaDTO = new PessoaDTO();
                pessoaDTO.setIdPessoa(resultSet.getInt("id_pessoa"));
                pessoaDTO.setNome(resultSet.getString("nome"));
                pessoaDTO.setCpf(resultSet.getLong("cpf"));
                pessoaDTO.setEndereco(resultSet.getString("endereco"));
                pessoaDTO.setSexo(resultSet.getString("sexo").charAt(0));
                pessoaDTO.setDtNasc(resultSet.getDate("dt_nasc"));
                listaPessoas.add(pessoaDTO);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceExceptions(e.getMessage(), e);

        }

        return listaPessoas;
    }

    @Override
    public PessoaDTO buscarPorId(Integer id) throws PersistenceExceptions {

        PessoaDTO pessoaDTO = null;

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT * FROM tb_pessoa WHERE id_pessoa=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            //IF because it?s just one person.
            if (resultSet.next()) {
                pessoaDTO = new PessoaDTO();
                pessoaDTO.setIdPessoa(resultSet.getInt("id_pessoa"));
                pessoaDTO.setNome(resultSet.getString("nome"));
                pessoaDTO.setCpf(resultSet.getLong("cpf"));
                pessoaDTO.setEndereco(resultSet.getString("endereco"));
                pessoaDTO.setSexo(resultSet.getString("sexo").charAt(0));
                pessoaDTO.setDtNasc(resultSet.getDate("dt_nasc"));
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceExceptions(e.getMessage(), e);
        }

        return pessoaDTO;
    }


    public List<PessoaDTO> consultList(String name, Long cpf, String sexo, String orderBy) throws PersistenceExceptions {

        List<PessoaDTO> list = new ArrayList<PessoaDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT * FROM tb_pessoa";

            boolean last = false;

            if (name != null && !name.equals("")) {
                sql += " WHERE nome LIKE ?";  //
                last = true;

            }

            if (cpf != null && !cpf.equals("")) {
                if (last) {
                    sql += " AND ";

                } else {
                    sql += " WHERE ";
                    last = true;
                }
                sql += " cpf LIKE ?";

            }

            if (sexo != null && !sexo.equals("")) {
                if (last) {
                    sql += " AND ";

                } else {
                    sql += " WHERE ";
                }
                sql += " sexo=?";
            }

            sql += " ORDER BY " + orderBy;
            PreparedStatement statement = connection.prepareStatement(sql);
            int cont = 0;

            if (name != null && !name.equals("")) {
                statement.setString(++cont, "%" + name + "%"); // '%' used to filter words with LIKE in our search.. (Read about LIKE in SQL)
            }

            if (cpf != null && !cpf.equals("")) {
                statement.setString(++cont, "%" + cpf + "%");
            }

            if (sexo != null && !sexo.equals("")) {
                statement.setString(++cont, sexo);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PessoaDTO pessoaDTO = new PessoaDTO();
                pessoaDTO.setIdPessoa(resultSet.getInt("id_pessoa"));
                pessoaDTO.setNome(resultSet.getString("nome"));
                pessoaDTO.setCpf(resultSet.getLong("cpf"));
                pessoaDTO.setDtNasc(resultSet.getDate("dt_nasc"));
                pessoaDTO.setEndereco(resultSet.getString("endereco"));
                pessoaDTO.setSexo(resultSet.getString("sexo").charAt(0));

                list.add(pessoaDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceExceptions(e.getMessage(), e);
        }

        return list;

    }*/
}
