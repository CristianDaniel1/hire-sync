import { useJobReportCompany } from '../../hooks/queries/useJobReportCompany';
import { partialDateFormatter } from '../../utils/formatting';

export const ReportCompany = ({ companyId }: { companyId: string }) => {
  const { data: jobReports } = useJobReportCompany({ id: companyId });

  if (!jobReports) return;

  return (
    <section className="bg-white rounded-xl custom-shadow p-6">
      <div className="flex justify-between items-center pb-4">
        <h2 className="text-2xl font-semibold">Relatório de Vagas</h2>
        <button className="bg-emerald-700 hover:bg-emerald-800 duration-200 rounded-md text-white font-medium px-12 py-2">
          Excel
        </button>
      </div>
      {jobReports.length === 0 ? (
        <p>Nenhum dado disponível para o relatório.</p>
      ) : (
        <div className="overflow-x-auto">
          <table className="w-full table-auto border text-sm text-left">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-4 py-2 font-medium">Título da Vaga</th>
                <th className="px-4 py-2 font-medium">Localização</th>
                <th className="px-4 py-2 font-medium">Expira em</th>
                <th className="px-4 py-2 font-medium">Candidaturas</th>
              </tr>
            </thead>
            <tbody>
              {jobReports.map(item => (
                <tr key={item.id} className="border-t">
                  <td className="px-4 py-2">{item.title}</td>
                  <td className="px-4 py-2">{item.location}</td>
                  <td className="px-4 py-2">
                    {partialDateFormatter(item.expiresAt)}
                  </td>
                  <td className="px-4 py-2">{item.totalApplications}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
};
